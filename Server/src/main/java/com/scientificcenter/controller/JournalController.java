package com.scientificcenter.controller;

import com.scientificcenter.model.dto.entity.AuthorsAndCodeBooksDto;
import com.scientificcenter.model.dto.entity.JournalViewDto;
import com.scientificcenter.model.dto.entity.PaperForUploadDto;
import com.scientificcenter.model.dto.process.FormSubmissionDto;
import com.scientificcenter.model.enums.PaymentMethod;
import com.scientificcenter.model.journal.ScientificAreaCodeBook;
import com.scientificcenter.model.journal.ScientificJournal;
import com.scientificcenter.model.paper.ScientificPaper;
import com.scientificcenter.model.users.User;
import com.scientificcenter.service.*;
import com.scientificcenter.util.HandlerFunctions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api/journals")
@Api("Controller for working with journals.")
@Log4j2
public class JournalController {

    private final JournalService journalService;
    private final ProcessService processService;
    private final HandlerFunctions handlerFunctions;
    private final FileStorageService fileStorageService;
    private final ScientificPaperService scientificPaperService;
    private final UserService userService;

    @Autowired
    public JournalController(JournalService journalService, ProcessService processService, HandlerFunctions handlerFunctions, FileStorageService fileStorageService, ScientificPaperService scientificPaperService, UserService userService) {
        this.journalService = journalService;
        this.processService = processService;
        this.handlerFunctions = handlerFunctions;
        this.fileStorageService = fileStorageService;
        this.scientificPaperService = scientificPaperService;
        this.userService = userService;
    }


    /*@ApiOperation(value = "Pay for some journal ")
    @PostMapping(value = "add-journal")
    public ResponseEntity addNewJournal(@RequestBody ScientificJournalElastic journalElastic) {
        ScientificJournalElastic scientificJournalElastic = this.journalService.save(journalElastic);
        return ResponseEntity.ok(scientificJournalElastic);
    }*/

    @ApiOperation(value = "Get all journals")
    @GetMapping
    public ResponseEntity getAllJournals() {
        List<ScientificJournal> journals = this.journalService.getAllJournals();
        List<JournalViewDto> journalViewDtos = new ArrayList<>();
        journals.forEach(journal -> {
            journalViewDtos.add(JournalViewDto.builder().id(journal.getId()).name(journal.getName())
                    .area(journal.getArea()).issnNumber(journal.getIssnNumber()).paymentMethod(journal.getPaymentMethod()).build());
        });
        return ResponseEntity.ok(journalViewDtos);
    }

    @ApiOperation(value = "Choose journal")
    @PostMapping("/choose/{processInstanceId}")
    public ResponseEntity chooseJournal(@RequestBody List<FormSubmissionDto> form,
                                        @PathVariable String processInstanceId) {
        TaskDto taskDto = this.processService.getTaskByProcessId(processInstanceId);
        String name = form.get(0).getFieldValue();
        Long id = Long.valueOf(form.get(1).getFieldValue());
        ScientificJournal journal = this.journalService.findJournalByIdAndName(id, name);
        VariableValueDto variable = new VariableValueDto();
        variable.setValue(journal.getPaymentMethod().name().equals(PaymentMethod.OPEN_ACCESS.name()) ? "true" : "false");
        variable.setType("Boolean");
        this.processService.putVariableForProcess("openAccess", taskDto.getProcessInstanceId(), variable);

        this.processService.submitForm(taskDto.getId(), form);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Get data for upload journal")
    @GetMapping("/data/{processInstanceId}")
    public ResponseEntity getDataForUpload(@PathVariable String processInstanceId) {
        VariableValueDto codeBooksVariable = this.processService.getVariableForProcess("codeBooks", processInstanceId);
        List<ScientificAreaCodeBook> codeBooks = (ArrayList<ScientificAreaCodeBook>) this.handlerFunctions
                .deserialize(codeBooksVariable.getValue().toString(),
                        ScientificAreaCodeBook.class);
        VariableValueDto authorsVariable = this.processService.getVariableForProcess("authors", processInstanceId);
        List<User> authors = (ArrayList<User>) this.handlerFunctions
                .deserialize(authorsVariable.getValue().toString(),
                        User.class);
        AuthorsAndCodeBooksDto resultDto = AuthorsAndCodeBooksDto.builder().authors(authors).codeBooks(codeBooks).build();
        return ResponseEntity.ok(resultDto);
    }

    @ApiOperation("Upload paper for journal (PDF)")
    @PostMapping(value = "/{journalId}/paper/{paperName}/upload/{processInstanceId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity uploadPaperPDF(@RequestPart("file") MultipartFile file,
                                         @PathVariable Long journalId,
                                         @PathVariable String paperName,
                                         @PathVariable String processInstanceId) {
        TaskDto task = this.processService.getTaskByProcessId(processInstanceId);
        String fileName = fileStorageService.store(file);
        ScientificJournal journal = this.journalService.findJournalById(journalId);
        ScientificPaper paper = this.scientificPaperService.getPaperByName(paperName);
        paper.setPathToPDF(fileName);
        paper = this.scientificPaperService.savePaper(paper);
        journal.getPapers().add(paper);
        this.journalService.save(journal);
        VariableValueDto variableValueDto = new VariableValueDto();
        variableValueDto.setType("String");
        variableValueDto.setValue(journal.getId().toString());
        this.processService.putVariableForProcess("journal", processInstanceId,variableValueDto);
        this.processService.serializeObjectAndSetAsVariable(paper,
                ScientificPaper.class.getTypeName(), processInstanceId, "paper");
        this.processService.submitForm(task.getId(), new ArrayList<FormSubmissionDto>());
        return ResponseEntity.ok().build();
    }

    @ApiOperation("Save paper for journal")
    @PostMapping(value = "/{journalId}/paper")
    public ResponseEntity uploadPaper(@RequestBody PaperForUploadDto scientificPaperDto,
                                      @PathVariable Long journalId) {
        ScientificPaper paper = scientificPaperService.uploadPaperDtoToEntity(scientificPaperDto);
        scientificPaperService.savePaper(paper);
        return ResponseEntity.ok(scientificPaperDto.getTitle());
    }

    @ApiOperation("Get paper for journal")
    @GetMapping("/{journalId}/paper/{paperName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String journalId, @PathVariable String paperName,
                                                 HttpServletRequest request) {
        ScientificPaper paper = this.scientificPaperService.getPaperByName(paperName);
        Resource file = this.fileStorageService.loadAsResource(paper.getPathToPDF());
        ;

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
