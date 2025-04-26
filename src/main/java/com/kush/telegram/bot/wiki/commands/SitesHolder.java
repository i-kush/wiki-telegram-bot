package com.kush.telegram.bot.wiki.commands;

public class SitesHolder extends Communicator<SitesHolder.Sites> {

    private static SitesHolder fSites;

    private SitesHolder(String aFile) {
        super(aFile);
    }

    public static void init(String aFile) {
        if (fSites == null) {
            fSites = new SitesHolder(aFile);
        }
    }

    public static SitesHolder getInstance() {
        return fSites;
    }

    public enum Sites implements Convertible {

        SITE_WIKI("site.wiki");

        private final String fSite;

        Sites(String aSite) {
            fSite = aSite;
        }

        public String getText() {
            return fSite;
        }
    }
}
