package pl.sages.jext.ch8.project;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Etap e (dla chętnych): zmień metody domyślne na deklaracje metod MapStruct.
 * Procesor wygeneruje implementację. DTO nie może zawierać danych karty.
 * Usuń fragment SZKIELET, a następnie odkomentuj deklarację pod nim.
 */
@Mapper
public interface ReportMapper {
    ReportMapper INSTANCE = Mappers.getMapper(ReportMapper.class);

    // TODO: mapowanie raportu do DTO.
    // SZKIELET:START (usuń razem z zaślepką)
    default ReportDto toDto(Report report) {
        throw new UnsupportedOperationException("TODO");
    }
    // SZKIELET:KONIEC
    // ROZWIĄZANIE (odkomentuj i usuń throw):
    // ReportDto toDto(Report report);
    // KONIEC ROZWIĄZANIA

    // TODO: bezpieczny DTO zamówienia, tylko id, klient i kwota.
    // SZKIELET:START (usuń razem z zaślepką)
    default OrderDto toSafeOrderDto(Order order) {
        throw new UnsupportedOperationException("TODO");
    }
    // SZKIELET:KONIEC
    // ROZWIĄZANIE (odkomentuj i usuń throw):
    // OrderDto toSafeOrderDto(Order order);
    // KONIEC ROZWIĄZANIA
}
