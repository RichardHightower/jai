package com.cloudurable.jai.model;

/**
 * A class that holds a secret value as a character array.
 */
public class SecretHolder {
    private final char[] secret;

    /**
     * Constructs a SecretHolder object with the provided secret.
     *
     * @param secret The secret value to be held.
     */
    public SecretHolder(String secret) {
        this.secret = secret.toCharArray();
    }

    /**
     * Retrieves the secret value as a String.
     *
     * @return The secret value.
     */
    public String getSecret() {
        return new String(secret);
    }

    /**
     * Checks if the secret value is empty.
     *
     * @return `true` if the secret value is empty, `false` otherwise.
     */
    public boolean isEmpty() {
        return secret.length <= 0;
    }
}
