//package com.ctrlaltdelinquents.backend.controller;
//
//import com.ctrlaltdelinquents.backend.model.Module;
//
//import com.ctrlaltdelinquents.backend.repo.ModuleRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
////import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/module")
//public class ModuleController {
//
//    @Autowired
//    private final ModuleRepo moduleRepo;
//
//    public ModuleController(ModuleRepo moduleRepo) {this.moduleRepo = moduleRepo;}
//
//    // GET all modules
//    @GetMapping("/all")
//    public List<Module> getAllModule() {
//        return moduleRepo.findAll();
//    }
//
////    // GET module by id
////    @GetMapping("/")
////    public Module getModule(@RequestParam int id) {
////        return moduleRepo.findById(id).get();
////    }
//
//}
