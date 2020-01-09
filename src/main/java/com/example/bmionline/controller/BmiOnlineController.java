package com.example.bmionline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BmiOnlineController {

    @GetMapping("/")
    public String home(){
        return "index";
    }

//    @GetMapping("/register")
//    public String register(){
//        return "register";
//    }

    @RequestMapping(value = "/resultbmi",method = {
            RequestMethod.GET,RequestMethod.POST
    })
    public String resultbmi(@RequestParam String bmi_gender,
                       @RequestParam Double weight,
                       @RequestParam Double height,
                       ModelMap map) {
        Double heightDividedByTen = height / 10;
        Double resultBeforeRounded = (weight/(heightDividedByTen*heightDividedByTen))*100;
        Double resultFinal = Math.round(resultBeforeRounded * 100.0) / 100.0;
        String explication="";
        String emoticon="";

        if (bmi_gender.equals("male")) {
            bmi_gender = "Mężczyzno, ";
        } else {
            bmi_gender= "Kobieto, ";
        }

        if (resultFinal <= 16) {
            explication = "Cierpisz na wygłodzenie! Zacznij jeść regularnie!";
            emoticon = "/smiles/f1.png";
        } else if ((resultFinal> 16) && (resultFinal<=16.99)){
            explication = "Jesteś wychudzony! Zjedz smacznie i obficie!";
            emoticon = "/smiles/f2.png";
        } else if ((resultFinal> 16.99) && (resultFinal<=18.49)){
            explication = "Cierpisz na niedowagę! Prawdopodobnie Twoje posiłki są niewystarczające!";
            emoticon = "/smiles/f4.png";
        } else if ((resultFinal> 18.49) && (resultFinal<=24.99)){
            explication = "Twoja relacja wagi do wzrostu jest prawidłowa. Nie musisz nic zmieniać.";
            emoticon = "/smiles/f9.png";
        } else if ((resultFinal> 24.99) && (resultFinal<=29.99)){
            explication = "Cierpisz na nadwagę! Jedz połowę!";
            emoticon = "/smiles/f4.png";
        } else if ((resultFinal> 29.99) && (resultFinal<=34.99)){
            explication = "Pierwszy stopień otyłości! Uważaj!!";
            emoticon = "/smiles/f3.png";
        } else if ((resultFinal> 34.99) && (resultFinal<=39.99)){
            explication = "Drugi stopień otyłości! Robi się nieciekawie!!";
            emoticon = "/smiles/f2.png";
        } else if (resultFinal > 39.99) {
            explication = "Cierpisz na skrajną otyłość! Zagraża to twojemu życiu i zdrowiu!";
            emoticon = "/smiles/f1.png";
        }

        if (weight>=1 || height>=1) {
            map.put("genderModel", bmi_gender);
            map.put("resultModel", resultFinal);
            map.put("explicationModel", explication);
            map.put("emoticonModel", emoticon);
        } else {
            map.put("resultModel", 0);
            map.put("explicationModel", "złe dane!!!");
        }
        return "resultbmi";
    }




    @RequestMapping(value = "/resultcalories",method = {
            RequestMethod.GET,RequestMethod.POST
    })
    public String resultcalories(@RequestParam String bmr_gender,
                            @RequestParam Double age,
                            @RequestParam Double weight,
                            @RequestParam Double height,
                            @RequestParam int activity,

                            ModelMap map) {
        Double resultFinalBmr;
        if (bmr_gender.equals("male")) {
            bmr_gender = "Mężczyzno, ";
            resultFinalBmr = 9.99*weight + 6.25*height - 4.92*age + 5;
        } else {
            bmr_gender= "Kobieto, ";
            resultFinalBmr = 9.99*weight + 6.25*height - 4.92*age - 161;
        }

        if (activity == 0) {
            resultFinalBmr=resultFinalBmr;
        } else if (activity == 1) {
            resultFinalBmr=resultFinalBmr*1.2;
        } else if (activity == 2) {
            resultFinalBmr=resultFinalBmr*1.4;
        } else if (activity == 3) {
            resultFinalBmr=resultFinalBmr*1.6;
        } else if (activity == 4) {
            resultFinalBmr=resultFinalBmr*1.8;
        } else if (activity == 5) {
            resultFinalBmr=resultFinalBmr*2.0;
        }

            int resultFinalBmrRounded = (int)Math.round(resultFinalBmr);

        map.put("genderModel", bmr_gender);
        map.put("resultBmrModel", resultFinalBmrRounded);
        return "resultcalories";
    }



}




