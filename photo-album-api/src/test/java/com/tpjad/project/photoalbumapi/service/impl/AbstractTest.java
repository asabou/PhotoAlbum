package com.tpjad.project.photoalbumapi.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public class AbstractTest {
    @BeforeEach
    void openMocks() {
        MockitoAnnotations.openMocks(this);
    }
}
