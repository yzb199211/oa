package com.huanxin.oa.view.select;

import androidx.annotation.NonNull;

public class FunctionViewBuilder {
    private String view_type;
    private String title;
    private String text;
    private String hint;

    private FunctionViewBuilder(Builder builder) {
        view_type = builder.view_type;
        title = builder.title;
        text = builder.text;
        hint = builder.hint;
    }


    public static final class Builder {
        private String view_type;
        private String title;
        private String text;
        private String hint;

        public Builder(@NonNull String view_type) {
        }

        public Builder view_type(String val) {
            view_type = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder text(String val) {
            text = val;
            return this;
        }

        public Builder hint(String val) {
            hint = val;
            return this;
        }

        public FunctionViewBuilder build() {
            return new FunctionViewBuilder(this);
        }
    }
}