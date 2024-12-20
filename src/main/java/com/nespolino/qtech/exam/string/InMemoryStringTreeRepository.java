package com.nespolino.qtech.exam.string;

import com.nespolino.qtech.exam.service.InMemoryTreeRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryStringTreeRepository extends InMemoryTreeRepository<String> {}
