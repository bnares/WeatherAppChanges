package view;

public enum Style {
    DARK("/css/darkTheme.css");

    final String path;

    Style(String path){
        this.path = path;
    }
}
