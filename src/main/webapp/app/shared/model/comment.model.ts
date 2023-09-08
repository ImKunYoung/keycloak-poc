import { IExhibition } from 'app/shared/model/exhibition.model';

export interface IComment {
  id?: number;
  voMember?: number | null;
  content?: string | null;
  artwork?: IExhibition | null;
}

export const defaultValue: Readonly<IComment> = {};
