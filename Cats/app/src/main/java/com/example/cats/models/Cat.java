package com.example.cats.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

// Cat model
public class Cat {

    public ArrayList<Breed> breeds;
    @SerializedName("url")
    public String imageUrl;

    public ArrayList<Breed> getBreeds() {
        return breeds;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public class Breed{
        public String id;
        public String name;
        public String description;
        public Weight weight;
        public String temperament;
        public String origin;
        @SerializedName("life_span")
        public String lifeSpan;
        @SerializedName("wikipedia_url")
        public String wikipediaLink;
        @SerializedName("child_friendly")
        public int childFriendly;
        @SerializedName("dog_friendly")
        public int dogFriendly;
        @SerializedName("stranger_friendly")
        public int strangerFriendly;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setWeight(Weight weight) {
            this.weight = weight;
        }

        public void setTemperament(String temperament) {
            this.temperament = temperament;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public void setLifeSpan(String lifeSpan) {
            this.lifeSpan = lifeSpan;
        }

        public void setWikipediaLink(String wikipediaLink) {
            this.wikipediaLink = wikipediaLink;
        }

        public void setChildFriendly(int childFriendly) {
            this.childFriendly = childFriendly;
        }

        public void setDogFriendly(int dogFriendly) {
            this.dogFriendly = dogFriendly;
        }

        public void setStrangerFriendly(int strangerFriendly) {
            this.strangerFriendly = strangerFriendly;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public Weight getWeight() {
            return weight;
        }

        public String getTemperament() {
            return temperament;
        }

        public String getOrigin() {
            return origin;
        }

        public String getLifeSpan() {
            return lifeSpan;
        }

        public String getWikipediaLink() {
            return wikipediaLink;
        }

        public int getChildFriendly() {
            return childFriendly;
        }

        public int getDogFriendly() {
            return dogFriendly;
        }

        public int getStrangerFriendly() {
            return strangerFriendly;
        }

        public class Weight{
            public String imperial;
            public String metric;

            public String getImperial() {
                return imperial;
            }

            public String getMetric() {
                return metric;
            }

            public void setImperial(String imperial) {
                this.imperial = imperial;
            }

            public void setMetric(String metric) {
                this.metric = metric;
            }
        }
    }

    @Override
    public String toString(){
        return  "{" +
                "\"breeds\":[" +
                "{" +
                "\"weight\":{" +
                "\"imperial\":\"" +  breeds.get(0).weight.imperial + "\"," +
                "\"metric\":\"" +  breeds.get(0).weight.metric +
                "\"}," +
                "\"id\":\"" + breeds.get(0).id + "\"," +
                "\"name\":\"" + breeds.get(0).getName() + "\"," +
                "\"temperament\":\"" + breeds.get(0).temperament + "\"," +
                "\"origin\":\"" + breeds.get(0).origin + "\"," +
                "\"description\":\"" +  breeds.get(0).description + "\"," +
                "\"life_span\":\"" + breeds.get(0).lifeSpan + "\"," +
                "\"child_friendly\":" + breeds.get(0).childFriendly + "," +
                "\"dog_friendly\":" + breeds.get(0).dogFriendly + "," +
                "\"stranger_friendly\":" + breeds.get(0).strangerFriendly + "," +
                "\"wikipedia_url\":\"" + breeds.get(0).wikipediaLink + "\"" +
                "}" +
                "]," +
                "\"url\":\"" + imageUrl +  "\"" +
                "}";
    }
}
