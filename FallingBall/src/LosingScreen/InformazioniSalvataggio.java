package LosingScreen;

public class InformazioniSalvataggio 
{
    String nomeUtente = "Nessun Salvataggio";
    double tempoOttenuto = 0.00;

    public InformazioniSalvataggio() {
    }
    
    public InformazioniSalvataggio(String nomeUtente, double tempoOttenuto) 
    {
        this.nomeUtente = nomeUtente;
        this.tempoOttenuto = tempoOttenuto;
    }
}