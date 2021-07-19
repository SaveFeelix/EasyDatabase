package de.savefeelix.database.enums;

public enum ReferenceTypes {
    Restrict("RESTRICT"),
    Cascade("CASCADE"),
    SetNull("SET NULL")
    ;

    private final String referenceType;

    ReferenceTypes(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getReferenceType() {
        return referenceType;
    }
}