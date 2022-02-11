package com.chubock.fileserver.enitity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FILES")
public class File {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotEmpty
    private String contentType;

    @NotNull
    @Positive
    private Long size;

    private byte[] content;

    private String uploader;

    @CreatedDate
    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    @Builder.Default
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate = LocalDateTime.now();

    @Version
    @Column(nullable = false)
    private Integer version;

}
