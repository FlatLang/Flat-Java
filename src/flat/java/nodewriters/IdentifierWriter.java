package flat.java.nodewriters;

import flat.tree.*;
import flat.tree.lambda.LambdaMethodDeclaration;
import flat.tree.variables.FieldDeclaration;
import flat.tree.variables.ObjectReference;
import flat.tree.variables.Variable;
import javafx.util.Pair;

import java.util.stream.Stream;

public abstract class IdentifierWriter extends ValueWriter implements AccessibleWriter {
    public abstract Identifier node();

    public boolean isExtensionDeclaration() {
        return node() instanceof Variable && getWriter((Variable)node()).isExtensionDeclaration();
    }

    public boolean hasPathToExtension() {
        return filterToExtensionSink(node().getAccessedNodes().stream()).findAny().isPresent();
    }

    private <TType extends Accessible> Stream<Variable> filterToExtensionSink(Stream<TType> stream) {
        return stream
            .filter(a -> a instanceof Variable)
            .map(a -> (Variable)a)
            .filter(v -> getWriter(v).isExtensionDeclaration());
    }

    public Pair<Identifier, Variable> getExtensionPath() {
        return getExtensionPath(true);
    }

    // FIXME: This needs to handle Accessibles as the path start
    public Pair<Identifier, Variable> getExtensionPath(boolean inclusive) {
        return filterToExtensionSink(node().getAccessedNodes().stream())
            .findFirst()
            .map(variable -> new Pair<>(inclusive ? node() : node().getAccessedNode(), variable))
            .orElse(null);
    }

    public StringBuilder writeExtensionUseExpression(StringBuilder builder, Identifier start) {
        throw new UnsupportedOperationException("should be a method call (" + node().getClass().getName() + "): " + node().getParentClass().getName() + "." + node().getParentMethod().getName());
    }

    @Override
    public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt) {
        if (node() == stopAt) return builder;

        if (node() != node().getReturnedNode() && node().getReturnedNode() instanceof Closure && !node().isAccessed()) {
            getWriter((Closure)node().getReturnedNode()).writeLambdaParams(builder);
        }

        if (node().doesAccess() && node().getAccessedNode().isChainNavigation()) {
            builder.append("FlatUtilities.chain(");
            writeUseExpression(builder).append(", _cr -> _cr.");
            return writeAccessedExpression(builder, stopAt, false).append(")");
        }

        if (hasPathToExtension()) {
            Pair<Identifier, Variable> path = getExtensionPath();

            if (path.getValue() != stopAt) {
                return getWriter(path.getValue()).writeExtensionUseExpression(builder, path.getKey());
            }
        }

        writeUseExpression(builder);
        writeAccessedExpression(builder, stopAt);

        return builder;
    }

    public StringBuilder writeExtensionReferenceAccess(StringBuilder builder) {
        if (node().getParentClass() instanceof ExtensionDeclaration == false) return builder;
        if (node().getReferenceTypeNode() instanceof ObjectReference == false) return builder;
        if (node().isAccessedWithinStaticContext()) return builder;

        return builder.append("_this.");
    }

    public StringBuilder writeUseExpression(StringBuilder builder) {
        writeExtensionReferenceAccess(builder);
        return writeName(builder).append(writeArrayAccess());
    }

    public final StringBuilder writeName() {
        return writeName(new StringBuilder());
    }

    public final StringBuilder writeName(StringBuilder builder) {
        return writeName(builder, null);
    }

    public StringBuilder writeName(StringBuilder builder, String name) {
        name = name != null ? name : node().getName();

        switch (name) {
            case "arguments": return builder.append("_java_arguments");
            case "public": return builder.append("_java_public");
            case "private": return builder.append("_java_private");
            case "package": return builder.append("_java_package");
            case "class": return builder.append("_java_class");
            case "default": return builder.append("_java_default");
            case "case": return builder.append("_java_case");
            case "function": return builder.append("_java_function");
            case "char": return builder.append("_java_char");
            default: return builder.append(name);
        }
    }

    public final StringBuilder writeOptionalName()
    {
        return writeOptionalName(new StringBuilder());
    }

    public final StringBuilder writeOptionalName(StringBuilder builder)
    {
        return writeOptionalName(builder, null);
    }

    public StringBuilder writeOptionalName(StringBuilder builder, String name)
    {
        return writeName(builder, name).append("_optional");
    }

}