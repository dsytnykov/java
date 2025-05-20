package org.example.useful.replaceif;

import org.example.useful.replaceif.product.Pasta;
import org.example.useful.replaceif.product.Pizza;
import org.example.useful.replaceif.product.Soup;

public enum ReplaceIfWithEnum {
    PASTA {
        @Override
        public void prepare() {
            new Pasta().prepare();
        }
    },
    SOUP {
        @Override
        public void prepare() {
            new Soup().prepare();
        }
    },
    PIZZA {
        @Override
        public void prepare() {
            new Pizza().prepare();
        }
    };

    public abstract void prepare();
}
