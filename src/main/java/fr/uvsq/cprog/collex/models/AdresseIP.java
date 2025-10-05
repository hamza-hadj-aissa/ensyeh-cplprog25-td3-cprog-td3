package fr.uvsq.cprog.collex.models;

/**
 * Classe représentant une adresse IP.
 * Elle valide le format de l'adresse lors de la création.
 */
public class AdresseIP {
    private final String value;

    public AdresseIP(String value) {
        if (!value.matches("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$")) {
            throw new IllegalArgumentException("Adresse IP invalide : " + value);
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
