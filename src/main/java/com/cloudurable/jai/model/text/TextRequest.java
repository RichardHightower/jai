package com.cloudurable.jai.model.text;

import com.cloudurable.jai.model.Request;

import java.util.Objects;

/**
 * The TextRequest class is an abstract class that serves as a base for text-related requests in the context
 * of an application. It implements the Request interface and provides common functionality and properties
 * for text-based requests.
 * <p>
 * This class represents a request for generating completions or performing operations on text data using a
 * specific model. It encapsulates information such as the model to be used, temperature and top-p values
 * for controlling randomness in the generated output, and the number of completions to generate for each prompt.
 * <p>
 * By extending this class and providing appropriate implementations for the abstract methods, developers can create
 * concrete subclasses to handle specific text-related requests in their application.
 * <p>
 * The TextRequest class provides methods to retrieve the model, temperature, top-p value, and completion count
 * associated with the text request. It also includes overridden equals(), hashCode(), and toString() methods
 * for comparing and representing instances of the TextRequest class.
 * <p>
 * Note that the TextRequest class is abstract and cannot be instantiated directly. It is intended to be extended by
 * subclasses that provide specific implementations based on the requirements of the application.
 */
public abstract class TextRequest implements Request {
    protected final String model;
    protected final float temperature;
    protected final float topP;
    protected final int completionCount;

    /**
     * Constructs a TextRequest object.
     *
     * @param model           The model used for the text request.
     * @param temperature     The temperature value for the text request.
     * @param topP            The top-p value for the text request.
     * @param completionCount The number of completions to generate for each prompt.
     */
    public TextRequest(String model, float temperature, float topP, int completionCount) {
        this.model = model;
        this.temperature = temperature;
        this.topP = topP;
        this.completionCount = completionCount;
    }

    /**
     * Returns the number of completions to generate for each prompt.
     *
     * @return The number of completions to generate for each prompt.
     */
    public int getN() {
        return completionCount;
    }

    /**
     * Returns the completion count for the text request.
     *
     * @return The completion count for the text request.
     */
    public int getCompletionCount() {
        return completionCount;
    }

    /**
     * Returns the model used for the text request.
     *
     * @return The model used for the text request.
     */
    public String getModel() {
        return model;
    }

    /**
     * Returns the temperature value for the text request.
     *
     * @return The temperature value for the text request.
     */
    public float getTemperature() {
        return temperature;
    }

    /**
     * Returns the top-p value for the text request.
     *
     * @return The top-p value for the text request.
     */
    public float getTopP() {
        return topP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextRequest)) return false;
        TextRequest that = (TextRequest) o;
        return Float.compare(that.temperature, temperature) == 0 &&
                Float.compare(that.topP, topP) == 0 &&
                completionCount == that.completionCount &&
                Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, temperature, topP, completionCount);
    }

    @Override
    public String toString() {
        return "TextRequest{" +
                "model='" + model + '\'' +
                ", temperature=" + temperature +
                ", topP=" + topP +
                ", completionCount=" + completionCount +
                '}';
    }
}
