package com.example.request_solver.model;

import com.example.request_solver.HasId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@ToString
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseEntity implements HasId {

    @Id
    protected Integer id;

    @CreationTimestamp
    protected LocalDateTime createdAt;

    @UpdateTimestamp
    protected LocalDateTime modifiedAt;

    @Version
    protected Integer version;

    // https://jpa-buddy.com/blog/hopefully-the-final-article-about-equals-and-hashcode-for-jpa-entities-with-db-generated-ids/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getEffectiveClass(this) != getEffectiveClass(o)) return false;
        return getId() != null && getId().equals(((BaseEntity) o).getId());
    }

    @Override
    public int hashCode() {
        return getEffectiveClass(this).hashCode();
    }

    private static Class<?> getEffectiveClass(Object o) {
        return o instanceof HibernateProxy ?
                ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    }
}