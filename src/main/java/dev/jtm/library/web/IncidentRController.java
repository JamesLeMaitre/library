package dev.jtm.library.web;

import dev.jtm.library.entities.Incident;
import dev.jtm.library.services.IncidentService;
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
@RequestMapping("/api/incident/")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class IncidentRController extends DataFormatter<Incident> {
    private final IncidentService incidentService;

    @PostMapping("create/{idDocument}")
    public Object create(@RequestBody() Incident data,@PathVariable Long idDocument){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.isAuthenticated()){
            try {
                return  renderData(true, incidentService.create(data,idDocument),"Create ");
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();
                return  renderStringData(false,"Error while processing" ,exceptionAsString);
            }
        } else return renderStringData(false,"Insufficient Authority", "Account not authenticated");


    }

    @PutMapping(value = "edit/{id}")
    public Object update(@PathVariable Long id, @RequestBody Incident  data) {
        try {
            if( incidentService.getById(id)==null){
                return  renderStringData(false,"Error while processing" ,"item not found");
            }
            return  renderData(true, incidentService.edit(data,id),"update done successfully");
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
            List<Incident> items = incidentService.getAll();
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
            Incident item = incidentService.getById(id);
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
            Incident item = incidentService.getById(id);
            if(item == null){
                return  renderStringData(false,"Error while processing" ,"item not found");
            }
            incidentService.delete(id);
            return  renderStringData(true,"Delete successfully","Done");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            return  renderStringData(false,"Error while processing" ,exceptionAsString);
        }
    }
}
