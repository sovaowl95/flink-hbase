package ru.test2.util;

import com.google.protobuf.Any;
import com.google.protobuf.BoolValue;
import com.google.protobuf.BytesValue;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import lombok.SneakyThrows;

public class UnpackUtil {
  @SneakyThrows
  public static Object unpack(final Any anyValue,
                              final String type) {
    switch (type) {
      case "String": {
        return anyValue.unpack(StringValue.class);
      }
      case "Integer": {
        return anyValue.unpack(Int64Value.class);
      }
      case "Boolean": {
        return anyValue.unpack(BoolValue.class);
      }
      case "Bytes": {
        return anyValue.unpack(BytesValue.class);
      }
      default:
        throw new RuntimeException("unknown type");
    }
  }
}
