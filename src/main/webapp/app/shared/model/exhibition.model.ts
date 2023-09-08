import { IComment } from 'app/shared/model/comment.model';
import { IView } from 'app/shared/model/view.model';
import { ILike } from 'app/shared/model/like.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IExhibition {
  id?: number;
  title?: string | null;
  location?: string | null;
  fee?: string | null;
  contact?: string | null;
  imgUrl?: string | null;
  voPeriod?: string | null;
  voArtist?: string | null;
  voMember?: string | null;
  status?: Status | null;
  comments?: IComment[] | null;
  views?: IView[] | null;
  likes?: ILike[] | null;
}

export const defaultValue: Readonly<IExhibition> = {};
