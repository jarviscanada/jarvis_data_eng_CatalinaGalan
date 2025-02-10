package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.repository.PositionRepository;
import ca.jrvs.apps.trading.repository.QuoteRepository;
import ca.jrvs.apps.trading.repository.TraderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

  @Autowired
  private TraderRepository traderRepository;

  @Autowired
  private QuoteRepository quoteRepository;

  @Autowired
  private PositionRepository positionRepository;


}
