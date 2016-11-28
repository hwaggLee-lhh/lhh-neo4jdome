package com.lhh.neo4j.core;


public abstract class AbstractEntity {
    public abstract void setId(Long id);


    public abstract Long getId();

@Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (getId() == null || obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        return getId().equals(((AbstractEntity) obj).getId());

    }

    @Override
    public int hashCode() {
        return getId() == null ? 0 : getId().hashCode();
    }
}
