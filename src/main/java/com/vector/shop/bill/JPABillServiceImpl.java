package com.vector.shop.bill;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JPABillServiceImpl implements BillService{
    private static final Logger log = LoggerFactory.getLogger(JPABillServiceImpl.class);
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Bill save(Bill bill) {
        bill.setCreatedDate(new Date());
        bill.getProducts().forEach(itemRepository::save);
        bill.calculateTotalprice();
        log.info(String.format("Total Price : %d\n",bill.getTotalPrice()));
        return billRepository.save(bill);
    }
    
}
