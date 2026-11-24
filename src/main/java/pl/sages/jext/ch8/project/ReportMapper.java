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
    ReportDto toDto(Report report);

    // TODO: bezpieczny DTO zamówienia, tylko id, klient i kwota.
    OrderDto toSafeOrderDto(Order order);
}
