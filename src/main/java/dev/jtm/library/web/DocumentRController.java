package dev.jtm.library.web;

import dev.jtm.library.entities.Document;
import dev.jtm.library.entities.Nature;
import dev.jtm.library.services.DocumentService;
import dev.jtm.library.services.NatureService;
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
@RequestMapping("/api/document/")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class DocumentRController extends DataFormatter<Document> {
    private final DocumentService documentService;

    @PostMapping("create/{idNature}/{idRayons}")
    public Object create(@RequestBody() Document data,@PathVariable Long idNature, @PathVariable Long idRayons){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //System.out.println(authentication.getAuthorities());
        if(authentication.isAuthenticated()){
            try {
                return  renderData(true, documentService.create(data,idNature,idRayons),"Create ");
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();
                return  renderStringData(false,"Error while processing" ,exceptionAsString);
            }
        } else return renderStringData(false,"Insufficient Authority", "Account not authenticated");


    }

    @PutMapping(value = "edit/{id}")
    public Object update(@PathVariable Long id, @RequestBody Document  data) {
        try {
            if( documentService.getById(id)==null){
                return  renderStringData(false,"Error while processing" ,"item not found");
            }
            return  renderData(true, documentService.edit(data,id),"update done successfully");
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
            List<Document> items = documentService.getAll();
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
            Document item = documentService.getById(id);
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
            Document item = documentService.getById(id);
            if(item == null){
                return  renderStringData(false,"Error while processing" ,"item not found");
            }
            documentService.delete(id);
            return  renderStringData(true,"Delete successfully","Done");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            return  renderStringData(false,"Error while processing" ,exceptionAsString);
        }
    }
}
