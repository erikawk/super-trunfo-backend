package br.senai.sc.newsupertrunfo.model.exception;

public class NotFoundCard extends RuntimeException{

    public NotFoundCard(){
        super("Carta não encontrada!");
    }
}
