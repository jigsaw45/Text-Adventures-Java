package io;

public class Command {
    private String verb;
    private String noun;

    public Command(String verb, String noun) {
        this.verb = verb;
        this.noun = noun;
    }

    public String getVerb(){
        return verb;
    }
    public String getNoun(){
        return noun;
    }
    public boolean hasNoun() {
        return noun !=null && !noun.isEmpty();
    }
}
