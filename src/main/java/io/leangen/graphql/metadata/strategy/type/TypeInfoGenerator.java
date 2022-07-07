package io.leangen.graphql.metadata.strategy.type;

import graphql.schema.GraphqlTypeComparatorRegistry;
import io.leangen.graphql.annotations.GraphQLScalar;
import io.leangen.graphql.annotations.types.GraphQLType;
import io.leangen.graphql.metadata.messages.MessageBundle;

import java.beans.Introspector;
import java.lang.reflect.AnnotatedType;
import java.util.Optional;

/**
 * @author Bojan Tomic (kaqqao)
 */
public interface TypeInfoGenerator {

    String INPUT_SUFFIX = "Input";
    String SCALAR_SUFFIX = "Scalar";

    String generateTypeName(AnnotatedType type, MessageBundle messageBundle);

    String generateTypeDescription(AnnotatedType type, MessageBundle messageBundle);

    default String generateInputTypeName(AnnotatedType type, MessageBundle messageBundle) {
        Boolean isStrictName = Optional.ofNullable(type.getAnnotation(GraphQLType.class))
                .map(GraphQLType::isStrictName).orElse(null);
        String inputPostfix = (isStrictName!=null && isStrictName) ? "" : INPUT_SUFFIX;
        return generateTypeName(type, messageBundle) + inputPostfix;
    }

    default String generateInputTypeDescription(AnnotatedType type, MessageBundle messageBundle) {
        return generateTypeDescription(type, messageBundle);
    }

    default String generateEnumTypeName(AnnotatedType type, MessageBundle messageBundle) {
        return generateTypeName(type, messageBundle);
    }

    default String generateEnumTypeDescription(AnnotatedType type, MessageBundle messageBundle) {
        return generateTypeDescription(type, messageBundle);
    }

    default String generateScalarTypeName(AnnotatedType type, MessageBundle messageBundle) {
        Boolean isStrictName = Optional.ofNullable(type.getAnnotation(GraphQLScalar.class))
                .map(GraphQLScalar::isStrictName).orElse(null);
        String scalarPostfix = (isStrictName!=null && isStrictName) ? "" : SCALAR_SUFFIX;
        return generateTypeName(type, messageBundle) + scalarPostfix;
    }

    default String generateScalarTypeDescription(AnnotatedType type, MessageBundle messageBundle) {
        return generateTypeDescription(type, messageBundle);
    }

    default String generateDirectiveTypeName(AnnotatedType type, MessageBundle messageBundle) {
        return Introspector.decapitalize(generateTypeName(type, messageBundle));
    }

    default String generateDirectiveTypeDescription(AnnotatedType type, MessageBundle messageBundle) {
        return generateTypeDescription(type, messageBundle);
    }

    GraphqlTypeComparatorRegistry generateComparatorRegistry(AnnotatedType type, MessageBundle messageBundle);
}
