package methode.tools;

import methode.metier.Client;

import java.util.Locale;

public class ProcessClient {

    public static Client normalizeAdresse (Client client){

        client.setPays(normalizePays(client));
        client.setVille(normalizeVille(client));
        client.setNoRue(normalizeNoRue(client));
        client.setCode_postal(normalizeCodePostal(client));
        client.setVoie(normalizeVoie(client));
        client.setNom(normalizeNom(client));
        client.setPrenom(normalizePrenom(client));

        return client;
    }

    public static String normalizeCodePostal(Client client) {
        String str = client.getCode_postal();
        str = str.replaceAll("[^\\d.]", "").trim();
        while (str.length() < 5) {
            str = "0" + str;
        }
        return str.substring(0,5);
    }

    public static String normalizePays(Client client){
        String pays = client.getPays();
        if(pays.equalsIgnoreCase("belgium")){
            return "Belgique";
        }else if (pays.equalsIgnoreCase("letzebuerg")){
            return "Luxembourg";
        }else if (pays.equalsIgnoreCase("Switzerland") || pays.equalsIgnoreCase("Schweiz")){
            return "Suisse";
        }else {
            return pays;
        }
    }

    public static String normalizeVille(Client client){
        String[][] toChangeContent = {
                {"st", "saint"},
                {"ste", "saint"},
        };

        String[] preposition = {
                "lès", "le", "sous", "sur", "à", "aux"
        };

        String[] str = client.getVille().toLowerCase().split(" ");

        //Pour chaque mots de la ville vérifie si l'un des mots est a changer par son non abbréviation
        for (int i = 0; i < str.length; i++) {
            for (String[] a : toChangeContent) {
                if (str[i].equals(a[0]) || (str[i].equals(a[0] + "."))) {
                    str[i] = a[1] + "-";
                    break;
                }
            }
            //Pour chaques mot vérifie si l'un d'eux fait partie des propositions et ajoute des tirets autour si oui
            for (int j = 0; j < preposition.length; j++) {
                if (str[i].equals(preposition[j])) {
                    str[i] = "-" + str[i] + "-";
                }
            }

        }

        for (int i = 0; i < str.length; i++) {
            for (int j = 0; j < preposition.length; j++) {
                if (!str[i].equals("-" + preposition[j] + "-")) {
                    str[i] = str[i].substring(0, 1).toUpperCase() + str[i].substring(1);
                }
            }
        }

        StringBuilder villeAjuste = new StringBuilder();
        for (int i = 0; i < str.length - 1; i++) {
            if (str[i+1].startsWith("-") || str[i].endsWith("-")) {
                villeAjuste.append(str[i]);
            } else {
                villeAjuste.append(str[i] + " ");
            }
        }
        villeAjuste.append(str[str.length-1]);

        return villeAjuste.toString();
    }

    public static String normalizeVoie(Client client) {
        String[][] toChangeContent = {
                {"boul", "boulevard"},
                {"bd", "boulevard"},
                {"av", "avenue"},
                {"faub", "faubourg"},
                {"fb", "faubourg"},
                {"pl", "place"}
        };

        String[] oldVoie = client.getVoie().toLowerCase().split(" ");

        //Pour chaque mots de la voie vérifie si l'un des mots est a changer par son non abbréviation
        for (int i = 0; i < oldVoie.length; i++) {
            for (String[] a : toChangeContent) {
                //Vérifie si l'un des mots de toChangeContent[x][0] correspond a un mot de abc et si oui le remplace par toChangeContent[x][1]
                if (oldVoie[i].equals(a[0]) || (oldVoie[i].equals(a[0] + "."))) {
                    oldVoie[i] = a[1];
                    break;
                }
            }
        }

        StringBuilder voieAjuste = new StringBuilder();
        for (String mot : oldVoie) {
            voieAjuste.append(mot).append(" ");
        }

        return voieAjuste.toString().trim();
    }



    public static String normalizeNoRue(Client client){
        if (client.getNoRue().contains(",")) {
            return client.getNoRue() +",";
        }else{
            return client.getNoRue();
        }
    }

    public  static String normalizeNom(Client client){
        return client.getNom().toUpperCase(Locale.ROOT);
    }
    public static String normalizePrenom(Client client){
        String prenom = client.getPrenom().trim();
        StringBuilder prenomSb = new StringBuilder();
        prenomSb.append(Character.toUpperCase(prenom.charAt(0)));
        prenomSb.append(prenom.substring(1));

        return prenomSb.toString();

    }
}
