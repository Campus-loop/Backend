package loopcampus.com.backend.dto.auth.response;


import loopcampus.com.backend.enumTypes.MemberRole;

// when a user requests their own information
public record MemberSummary(
        Long id,
        String email,
        MemberRole memberRole
){
}
