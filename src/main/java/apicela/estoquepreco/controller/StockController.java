package apicela.estoquepreco.controller;

import apicela.estoquepreco.constants.RabbitmqConsts;
import apicela.estoquepreco.dto.PriceDTO;
import apicela.estoquepreco.dto.StockDTO;
import apicela.estoquepreco.service.RabbitmqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stock")
public class StockController {

    @Autowired
    RabbitmqService rabbitmqService;

    @PutMapping
    private ResponseEntity update(@RequestBody StockDTO stockDTO) {
        this.rabbitmqService.sendMessage(RabbitmqConsts.STOCK_QUEUE, stockDTO);
        System.out.println("sended Message: " + stockDTO);
        return new ResponseEntity(HttpStatus.OK);
    }
}
