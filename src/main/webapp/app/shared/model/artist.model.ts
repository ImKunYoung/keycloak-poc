import { IArtistComment } from 'app/shared/model/artist-comment.model';
import { IArtistView } from 'app/shared/model/artist-view.model';
import { IArtistLike } from 'app/shared/model/artist-like.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IArtist {
  id?: number;
  name?: string | null;
  realName?: string | null;
  imgUrl?: string | null;
  phone?: string | null;
  career?: string | null;
  voArtwork?: string | null;
  voMember?: string | null;
  status?: Status | null;
  artistcomments?: IArtistComment[] | null;
  artistviews?: IArtistView[] | null;
  artistlikes?: IArtistLike[] | null;
}

export const defaultValue: Readonly<IArtist> = {};
