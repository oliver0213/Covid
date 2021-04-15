package com.example.codejava;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.aspectj.weaver.loadtime.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequestMapping("hc")
public class PatientApi {

    @Autowired
    private PatientRepository repository;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("patient")
    public String index() {
        return "HC_PatientList";
    }

    @GetMapping("patient/update/{id}")
    public String updateIndex(@PathVariable("id") Long id, Model model) {
        transactionRepository.findById(id).ifPresent(t -> {
            model.addAttribute("patient", t);
        });
        return "HC_UpdatePatientRecord";
    }


    @GetMapping("patients")
    @ResponseBody
    public List<Transaction> findAll(@RequestParam(value = "keyword",
            required = false) String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return transactionRepository.findAll();
        }else {
            final var key = "%" + keyword + "%";
            return transactionRepository.findByPatientIdLikeOrUserFullnameLike(key,key);
        }
    }


    @PostMapping("patients/{id}")
    @ResponseBody
    public ResponseEntity upload(@PathVariable("id") Long id,
                                 @RequestParam(value = "status", required = false) String status
            ,
                                 MultipartFile file) {

        try (InputStream inputStream = file.getInputStream();
        ) {
            byte[] imgdata = inputStream.readAllBytes();
            transactionRepository.findById(id).ifPresent((p) ->
                    {
                        p.setStatus("completed");
                        p.setImage(imgdata);
                        transactionRepository.save(p);
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }


        return ResponseEntity.ok("true");
    }
}
