package ru.exceptions;

import java.io.IOException;

public class MigrationException extends RuntimeException {
  public MigrationException(IOException e) {
    super(e);
  }
}
