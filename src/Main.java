import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Tache {
    private  int numero;
    private String description;
    private  int statut;
    private String dateLimite;

    public Tache(int numero, String description, int statut, String dateLimite) {
        this.numero = numero;
        this.description = description;
        this.statut = statut;
        this.dateLimite = dateLimite;
    }

    public int getNumero() {
        return numero;
    }

    public String getDescription() {
        return description;
    }

    public int getStatut() {
        return statut;
    }

    public String getDateLimite() {
        return dateLimite;
    }

    @Override
    public String toString() {
        return "Tache{" +
                "numero=" + numero +
                ", description='" + description + '\'' +
                ", statut=" + statut +
                ", dateLimite='" + dateLimite + '\'' +
                '}';
    }
}

public class Main {
    private static final List<Tache> taches = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final String Taches = "taches.txt";

    public static void main(String[] args) {
        System.out.println("Bonjour à vous, faites un choix ci-dessous");

        int choix;

        do {
            afficherMenu();
            System.out.print("Choix: ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    ajouterTache();
                    break;
                case 2:
                    supprimerTache();
                    break;
                case 3:
                    afficherTachesTerminees();
                    break;
                case 4:
                    enregistrerTachesDansFichier();
                    break;
                case 5:
                    chargerTachesDepuisFichier();
                    break;
                case 6:
                    afficherTachesEntreDates();
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Choix invalide !");
            }
        } while (choix != 7);

        scanner.close();
    }

    private static void afficherMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Ajouter une tâche");
        System.out.println("2. Supprimer une tâche");
        System.out.println("3. Afficher la liste des tâches finies (statut = 1)");
        System.out.println("4. Enregistrer la liste des tâches dans un fichier");
        System.out.println("5. Charger la liste des tâches depuis un fichier");
        System.out.println("6. Afficher la liste des tâches dont la date est comprise entre 2 dates saisies par l'utilisateur");
        System.out.println("7. Quitter");
    }

    private static void ajouterTache() {
        System.out.println("Saisir les détails de la tâche :");
        System.out.print("Numéro : ");
        int numero = scanner.nextInt();
        scanner.nextLine(); //
        System.out.print("Description : ");
        String description = scanner.nextLine();
        System.out.print("Statut (1 pour terminé, 0 pour non terminé) : ");
        int statut = scanner.nextInt();
        scanner.nextLine();

        String dateLimite = "";
        boolean dateValide = false;
        while (!dateValide) {
            System.out.print("Date limite (format : JJ/MM/AAAA) : ");
            dateLimite = scanner.nextLine();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);
                sdf.parse(dateLimite);
                dateValide = true;
            } catch (ParseException e) {
                System.out.println("Format de date incorrect. Veuillez saisir une date au format JJ/MM/AAAA.");
            }
        }

        Tache tache = new Tache(numero, description, statut, dateLimite);
        taches.add(tache);
        System.out.println("Tâche ajoutée avec succès !");
    }


    private static void supprimerTache() {
        System.out.print("Numéro de la tâche à supprimer : ");
        int numero = scanner.nextInt();

        Iterator<Tache> iterator = taches.iterator();
        while (iterator.hasNext()) {
            Tache tache = iterator.next();
            if (tache.getNumero() == numero) {
                iterator.remove();
                System.out.println("Tâche supprimée avec succès !");
                return;
            }
        }

        System.out.println("Aucune tâche trouvée avec ce numéro.");
    }

    private static void afficherTachesTerminees() {
        for (Tache tache : taches) {
            if (tache.getStatut() == 1) {
                System.out.println(taches);
            }
        }
    }

    private static void enregistrerTachesDansFichier() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(Taches))) {
            for (Tache tache : taches) {
                writer.println(tache.getNumero() + "," + tache.getDescription() + "," + tache.getStatut() + "," + tache.getDateLimite());
            }
            System.out.println("Liste des tâches enregistrée dans le fichier '" + Taches + "'.");
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture des tâches dans le fichier : " + e.getMessage());
        }
    }
    private static void chargerTachesDepuisFichier() {
        taches.clear();
        File file = new File("taches.txt");
        if (file.exists()) {
            try (Scanner fileScanner = new Scanner(file)) {
                System.out.println("Liste des tâches :");
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split(",");
                    int numero = Integer.parseInt(parts[0]);
                    String description = parts[1];
                    int statut = Integer.parseInt(parts[2]);
                    String dateLimite = parts[3];
                    Tache tache = new Tache(numero, description, statut, dateLimite);
                    taches.add(tache);
                    System.out.println(tache.getNumero() + ", " + tache.getDescription() + ", " + tache.getStatut() + ", " + tache.getDateLimite());
                }
            } catch (FileNotFoundException e) {
                System.out.println("Le fichier 'taches.txt' est introuvable.");
            }
        } else {
            System.out.println("Le fichier 'taches.txt' n'existe pas.");
        }
    }
    private static void afficherTachesEntreDates() {
        System.out.println("Saisir la date de début (format : JJ/MM/AAAA) : ");
        String debut = scanner.nextLine();
        System.out.println("Saisir la date de fin (format : JJ/MM/AAAA) : ");
        String fin = scanner.nextLine();

        System.out.println("Liste des tâches entre " + debut + " et " + fin + " :");

        for (Tache tache : taches) {
            if (estEntreDeuxDates(tache.getDateLimite(), debut, fin)) {
                System.out.println(tache.getNumero() + ", " + tache.getDescription() + ", " + tache.getStatut() + ", " + tache.getDateLimite());
            }
        }
    }

    private static boolean estEntreDeuxDates(String date, String debut, String fin) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dateTache = sdf.parse(date);
            Date dateDebut = sdf.parse(debut);
            Date dateFin = sdf.parse(fin);

            return dateTache.after(dateDebut) && dateTache.before(dateFin) || dateTache.equals(dateDebut) || dateTache.equals(dateFin);
        } catch (ParseException e) {
            System.out.println("Format de date incorrect : " + e.getMessage());
            return false;
        }
    }


}

