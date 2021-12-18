package capture;

public record NetworkInterface(String name, String description) {

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
