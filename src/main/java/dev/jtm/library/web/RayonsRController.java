package dev.jtm.library.web;

import dev.jtm.library.entities.Rayons;
import dev.jtm.library.servicesimpl.RayonsServiceImpl;
import dev.jtm.library.utils.DataFormatter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestController
@RequestMapping("/api/rayons")
@AllArgsConstructor
public class RayonsRController extends DataFormatter<Rayons> {
    private final RayonsServiceImpl rayonsService;

    @PostMapping("/create")
    public Object create(@RequestBody() Rayons data){
        try {
            return  renderData(true, rayonsService.create(data),"Create ");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            return  renderStringData(false,"Error while processing" ,exceptionAsString);
        }

    }
}
