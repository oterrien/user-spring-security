package com.ote.user.api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class PerimeterPath implements Iterable<String> {

    @Getter
    private final String[] path;

    @Override
    public String toString() {
        return Arrays.stream(path).collect(Collectors.joining("/"));
    }

    @Override
    public Iterator<String> iterator() {
        return Arrays.stream(path).iterator();
    }

    public static class Builder {

        private final List<String> path = new ArrayList<>();

        public Builder(String element) {
            this.path.add(element);
        }

        public Builder then(String element) {
            this.path.add(element);
            return this;
        }

        public PerimeterPath build() {
            return new PerimeterPath(path.toArray(new String[0]));
        }
    }
}
