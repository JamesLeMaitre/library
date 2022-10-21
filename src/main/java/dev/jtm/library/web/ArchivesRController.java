package dev.jtm.library.web;

import dev.jtm.library.entities.Archives;
import dev.jtm.library.entities.Consultation;
import dev.jtm.library.services.ArchivesService;
import dev.jtm.library.utils.DataFormatter;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/archives/")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class ArchivesRController extends DataFormatter<Archives> {
    private  final ArchivesService archivesService;

    @PostMapping("create/{idDocument}")
    public Object create(@RequestBody() Archives data, @PathVariable Long idDocument){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.isAuthenticated()){
            try {
                return  renderData(true, archivesService.create(data,idDocument),"Create ");
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();
                return  renderStringData(false,"Error while processing" ,exceptionAsString);
            }
        } else return renderStringData(false," Not Authenticated", "Account not authenticated");


    }

    @PutMapping(value = "edit/{id}")
    public Object update(@PathVariable Long id, @RequestBody Archives  data) {
        try {
            if( archivesService.getById(id)==null){
                return  renderStringData(false,"Error while processing" ,"item not found");
            }
            return  renderData(true, archivesService.edit(data,id),"update done successfully");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            return  renderStringData(false,"Error while processing" ,exceptionAsString);
        }
    }

    @GetMapping("list")
    public Object List(){
        try {
            List<Archives> items = archivesService.getAll();
            return  renderDataArray(true,items,"list of element");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            return  renderStringData(false,"Error while processing" ,exceptionAsString);
        }
    }

    @GetMapping("by/id/{id}")
    public Object getById(@PathVariable("id") Long id){
        try {
            Archives item = archivesService.getById(id);
            if(item == null){
                return  renderStringData(false,"Error while processing" ,"item not found");
            }
            return  renderData(true,item,"Element found");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            return  renderStringData(false,"Error while processing" ,exceptionAsString);
        }
    }

    @DeleteMapping("delete/{id}")
    public Object delete(@PathVariable("id") Long id){
        try {
            Archives item = archivesService.getById(id);
            if(item == null){
                return  renderStringData(false,"Error while processing" ,"item not found");
            }
            archivesService.delete(id);
            return  renderStringData(true,"Delete successfully","Done");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            return  renderStringData(false,"Error while processing" ,exceptionAsString);
        }
    }
}
