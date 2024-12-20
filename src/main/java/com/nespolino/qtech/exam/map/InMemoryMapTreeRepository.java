package com.nespolino.qtech.exam.map;

import com.nespolino.qtech.exam.service.InMemoryTreeRepository;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryMapTreeRepository extends InMemoryTreeRepository<Map<String, Object>> {}
