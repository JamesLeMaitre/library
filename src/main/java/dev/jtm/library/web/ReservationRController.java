package dev.jtm.library.web;

import dev.jtm.library.entities.Incident;
import dev.jtm.library.entities.Rayons;
import dev.jtm.library.entities.Reservation;
import dev.jtm.library.services.DocumentService;
import dev.jtm.library.services.ReservationService;
import dev.jtm.library.services.security.AppUsersService;
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
@RequestMapping("/api/reservation/")
@AllArgsConstructor
public class ReservationRController extends DataFormatter<Reservation> {
    private final ReservationService reservationService;


    @PostMapping("create/{idDocument}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Object create(@RequestBody() Reservation data, @PathVariable Long idDocument){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()){
            try {
                return  renderData(true, reservationService.create(data,idDocument),"Create ");
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();
                return  renderStringData(false,"Error while processing" ,exceptionAsString);
            }
        } else return renderStringData(false,"Not authenticated", "Account not authenticated");
    }

    @GetMapping("list")
    public Object List(){
        try {
            List<Reservation> items = reservationService.getAll();
            return  renderDataArray(true,items,"list of element");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            return  renderStringData(false,"Error while processing" ,exceptionAsString);
        }
    }

    @GetMapping("listActiveReservation")
    public Object ListActiveRservation(){
        try {
            List<Reservation> items = reservationService.getAllNotDelete();
            return  renderDataArray(true,items,"list of element");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            return  renderStringData(false,"Error while processing" ,exceptionAsString);
        }
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("delete/{id}")
    public Object delete(@PathVariable("id") Long id){
        try {
            Reservation item = reservationService.getById(id);
            if(item == null){
                return  renderStringData(false,"Error while processing" ,"item not found");
            }
            reservationService.delete(id);
            return  renderStringData(true,"Delete successfully","Done");
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
            Reservation item = reservationService.getById(id);
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

}
