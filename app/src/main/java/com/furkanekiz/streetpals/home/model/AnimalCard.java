
package com.furkanekiz.streetpals.home.model;

public class AnimalCard {

    private Type type;
    private String title;
    private String description;
    private String extraText;
    private String localImageResource;
    private String urlToSite;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtraText() {
        return extraText;
    }

    public void setExtraText(String extraText) {
        this.extraText = extraText;
    }

    public String getLocalImageResource() {
        return localImageResource;
    }

    public void setLocalImageResource(String localImageResource) {
        this.localImageResource = localImageResource;
    }

    public String getUrlToSite() {
        return urlToSite;
    }

    public void setUrlToSite(String urlToSite) {
        this.urlToSite = urlToSite;
    }

    public enum Type {
        ANIMAL;

        Type() {
        }
    }
}
