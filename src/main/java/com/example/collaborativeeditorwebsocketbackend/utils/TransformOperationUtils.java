package com.example.collaborativeeditorwebsocketbackend.utils;

import com.example.collaborativeeditorwebsocketbackend.entity.Operation;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TransformOperationUtils {

    public static Operation transformOperation(Operation incomingOperation, List<Operation> appliedOperations) {
        var transformedOperation = Operation.createOperationClone(incomingOperation);

        var operationsToLookWhenTransforming = appliedOperations.stream()
                .filter(operation -> operation.getVersion() >= incomingOperation.getVersion()).sorted(Comparator.comparing(Operation::getVersion)).collect(Collectors.toList());

        operationsToLookWhenTransforming.forEach(appliedOperation -> {
            if(transformedOperation.getPosition() >= appliedOperation.getPosition()) {
                if(appliedOperation.getType().equals("insert")) {
                    transformedOperation.setPosition(transformedOperation.getPosition() + 1);
                } else {
                    if(transformedOperation.getPosition() > 0) {
                        transformedOperation.setPosition(transformedOperation.getPosition() - 1);
                    }
                }
            }
        });

        return transformedOperation;
    }
}
