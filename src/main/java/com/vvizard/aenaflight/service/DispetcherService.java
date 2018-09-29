package com.vvizard.aenaflight.service;

import com.vvizard.aenaflight.model.Dispetcher;
import com.vvizard.aenaflight.repository.DestinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DispetcherService {

    @NotNull
    private final DestinationRepository destinationRepository;

    @NotNull
    private final DestinationService destinationService;

    @NotNull
    private final Dispetcher dispetcher;




    private LocalDate curDate=null;



    public Boolean equalDate(LocalDate date){
        if (curDate==null){
            curDate=date;
            return true;
        }else if(curDate.equals(date)){
            return true;
        }
        curDate=date;
        return false;
    }

}
