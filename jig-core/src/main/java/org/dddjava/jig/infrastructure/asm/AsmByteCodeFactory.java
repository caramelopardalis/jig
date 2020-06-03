package org.dddjava.jig.infrastructure.asm;

import org.dddjava.jig.domain.model.jigsource.file.binary.ClassSource;
import org.dddjava.jig.domain.model.jigsource.file.binary.ClassSources;
import org.dddjava.jig.domain.model.jigsource.jigloader.analyzed.ByteCodeFactory;
import org.dddjava.jig.domain.model.jigsource.jigloader.analyzed.TypeByteCode;
import org.dddjava.jig.domain.model.jigsource.jigloader.analyzed.TypeByteCodes;
import org.objectweb.asm.ClassReader;

import java.util.ArrayList;
import java.util.List;

public class AsmByteCodeFactory implements ByteCodeFactory {

    @Override
    public TypeByteCodes readFrom(ClassSources classSources) {
        List<TypeByteCode> list = new ArrayList<>();
        for (ClassSource source : classSources.list()) {
            TypeByteCode typeByteCode = typeByteCode(source);
            list.add(typeByteCode);
        }
        return new TypeByteCodes(list);
    }

    TypeByteCode typeByteCode(ClassSource classSource) {
        AsmClassVisitor asmClassVisitor = new AsmClassVisitor();
        ClassReader classReader = new ClassReader(classSource.value());
        classReader.accept(asmClassVisitor, ClassReader.SKIP_DEBUG);
        return asmClassVisitor.typeByteCode;
    }
}