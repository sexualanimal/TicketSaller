//package ua.kozak.solowork.service.impl;
//
////@Service
//public class AuditoriumServiceImpl {
//
////private StorageService<Set<Auditory>>storageService;
////
////public AuditoriumServiceImpl(){
////        storageService=new StorageServiceImpl<>(FILENAME);
////        }
////
////@Override
////public Set<Auditory> getAll(){
////        return storageService.read();
////        }
////
////@Override
////public Auditory getByAllName(String name)throws AuditoriumNotExistsException{
////        return getAll().stream()
////        .filter(a->a.getName().equals(name))
////        .findFirst()
////        .orElseThrow(AuditoriumNotExistsException::new);
////        }
////
////@Override
////public Auditory getById(long id)throws AuditoriumNotExistsException{
////        return getAll().stream()
////        .filter(a->a.getId()==id)
////        .findFirst()
////        .orElseThrow(AuditoriumNotExistsException::new);
////        }
////
////@Override
////public Auditory save(Auditory auditory)throws AuditoriumAlreadyExistsException{
////        if(checkAuditoriumUnique(auditory)){
////        Set<Auditory> buffAuditories=getAll();
////        buffAuditories.add(auditory);
////        storageService.write(buffAuditories);
////        return getAll().stream()
////        .filter(u->u.equals(auditory))
////        .findFirst().orElse(auditory);
////        }else{
////        throw new AuditoriumAlreadyExistsException();
////        }
////        }
////
////@Override
////public Set<Auditory> saveAll(Set<Auditory> auditories,boolean overwrite)throws AuditoriumAlreadyExistsException{
////        boolean auditoriumsValid=auditories.stream()
////        .map(this::checkAuditoriumUnique)
////        .filter(b->b.equals(false))
////        .collect(Collectors.toSet())
////        .isEmpty();
////        if(auditoriumsValid){
////        if(overwrite){
////        return storageService.write(auditories);
////        }else{
////        Set<Auditory> buffAuditories=getAll();
////        buffAuditories.addAll(auditories);
////        return storageService.write(buffAuditories);
////        }
////        }else{
////        throw new AuditoriumAlreadyExistsException();
////        }
////        }
////
////@Override
////public Auditory remove(Auditory auditory)throws AuditoriumNotExistsException{
////        Set<Auditory> buffAuditories=getAll();
////        boolean isDeleted=buffAuditories.removeIf(m->m.equals(auditory));
////        if(!isDeleted)throw new AuditoriumNotExistsException();
////        storageService.write(buffAuditories);
////        return auditory;
////        }
////
////@Override
////public boolean checkAuditoriumUnique(Auditory auditory){
////        return getAll().stream()
////        .filter(a->a.equals(auditory))
////        .collect(Collectors.toSet())
////        .isEmpty();
////        }
////
////@Override
////public long getNextEmptyId(){
////        return getAll().stream().mapToLong(Auditory::getId).max().orElse(-1)+1;
////        }
//}
