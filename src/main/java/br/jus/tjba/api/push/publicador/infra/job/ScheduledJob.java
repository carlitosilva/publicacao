package br.jus.tjba.api.push.publicador.infra.job;

import br.jus.tjba.api.push.publicador.domain.services.PublicadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledJob {


    @Autowired
    private PublicadorService service;
    @Scheduled(cron = "0 */1 * * * *")
    public void execute()  throws Exception {

        service.enviarMensagensPendentes();
        System.out.println("Passou aqui +" + String.valueOf(System.currentTimeMillis())) ;
    }
    /* @Autowired
  JobLauncher jobLauncher;

  @Autowired
  Job job;

  @Scheduled(cron = "0 * /1 * * * ?")
    public void perform() throws Exception
    {
        JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

        jobLauncher.run(job, params);
    }*/
}
