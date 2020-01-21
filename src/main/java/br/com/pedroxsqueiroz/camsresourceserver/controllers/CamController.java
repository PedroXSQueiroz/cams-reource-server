package br.com.pedroxsqueiroz.camsresourceserver.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.pedroxsqueiroz.camsresourceserver.models.CamModel;

@Controller
@RequestMapping(value = "cams")
@CrossOrigin("*")
public class CamController extends AbstractNodeController<CamModel>{

}
