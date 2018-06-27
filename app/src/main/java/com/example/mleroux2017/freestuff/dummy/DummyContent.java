package com.example.mleroux2017.freestuff.dummy;

import com.example.mleroux2017.freestuff.ControllersFirebase.AnnonceControllerFirebase;
import com.example.mleroux2017.freestuff.Objects.Annonce;
import com.example.mleroux2017.freestuff.Objects.Categorie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Annonce> ITEMS = new ArrayList<Annonce>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Annonce> ITEM_MAP = new HashMap<String, Annonce>();

    //private static final int COUNT = 25;

    public void init(){
        AnnonceControllerFirebase acf = new AnnonceControllerFirebase();
        acf.getAll(new AnnonceControllerFirebase.OnTabListener() {
            @Override
            public void onGetTabListener(ArrayList<Annonce> tab) {
                if(tab.size()>0){
                    for (int i = 1; i <= tab.size(); i++) {
                        addItem(tab.get(i));
                    }
                }
            }
        });
    }

      private static void addItem(Annonce item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    private static Annonce createDummyItem(int position) {
        /* test avant extraction de la base de donnée */
        String titre = "Exemple "+position;
        String description = "Lorem ipsum dolor sit amet,consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        String etatArticle = "Bon état";
        Categorie categorie = new Categorie("Décoration");
        return new Annonce(String.valueOf(position), titre, description, etatArticle, categorie);
        //return new Annonce(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

}
