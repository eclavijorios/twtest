package model;


public class Village {
    private String name;

    public Village(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    @Override

    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Village village = (Village) o;

        return name != null ? name.equals(village.name) : village.name == null;
    }
}
