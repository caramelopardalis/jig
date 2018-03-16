package jig.domain.model.thing;

import java.util.Objects;

public class Name {

    String value;

    public Name(Class<?> clz) {
        this(clz.getName());
    }

    public Name(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public String asCompressText() {
        return value.replaceAll("(\\w)\\w+\\.", "$1.");
    }

    public String asSimpleText() {
        return value.replaceAll("([\\w]+\\.)*(\\w+)", "$2");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name that = (Name) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public Name concat(Name other) {
        // カッコより手前のドット以降
        String substring = other.value.substring(other.value.lastIndexOf(".", other.value.lastIndexOf("(")));
        return new Name(value + substring);
    }
}
